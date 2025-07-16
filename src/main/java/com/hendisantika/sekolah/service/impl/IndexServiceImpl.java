package com.hendisantika.sekolah.service.impl;

import com.hendisantika.sekolah.dto.UserAgentInfo;
import com.hendisantika.sekolah.entity.*;
import com.hendisantika.sekolah.repository.*;
import com.hendisantika.sekolah.service.IndexService;
import com.hendisantika.sekolah.util.WebUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private static final UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private final TulisanRepository tulisanRepository;
    private final PengumumanRepository pengumumanRepository;
    private final AgendaRepository agendaRepository;
    private final GuruRepository guruRepository;
    private final FilesRepository filesRepository;
    private final SiswaRepository siswaRepository;
    private final KategoriRepository kategoriRepository;
    private final KomentarRepository komentarRepository;
    private final GaleriRepository galeriRepository;
    private final PengunjungRepository pengunjungRepository;

    @Value("${cookie.maxAge}")
    private Integer cookieMaxAge;

    @Override
    public void createCookieAndSave(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        String remoteIpAddr = "";
        String remoteHostAddr = "";
        if (request != null) {
            remoteIpAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteIpAddr == null || remoteIpAddr.isEmpty()) {
                remoteIpAddr = request.getRemoteAddr();
                remoteHostAddr = request.getRemoteHost();
            }
        }
        log.info("Creating a cookie for {}.", cookieName);
        Cookie cookie = new Cookie(cookieName, RequestContextHolder.currentRequestAttributes().getSessionId());
        cookie.setMaxAge(cookieMaxAge);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // global cookie accessible everywhere
        UserAgentInfo userAgentInfo = WebUtils.showUserAgentInfo(parser.parse(Objects.requireNonNull(request).getHeader("User-Agent")));
        userAgentInfo.setHostAddress(remoteIpAddr);
        userAgentInfo.setHostName(remoteHostAddr);

        Pengunjung pengunjung = Pengunjung.builder()
                .sessionId(cookie.getValue())
                .cookieName(cookie.getName())
                .url(request.getRequestURI())
                .ipAddress(userAgentInfo.getHostAddress())
                .osType(userAgentInfo.getOsType())
                .osVersion(userAgentInfo.getOsVersion())
                .browserName(userAgentInfo.getBrowserName())
                .browserType(userAgentInfo.getBrowserType())
                .browserVersion(userAgentInfo.getBrowserVersion())
                .deviceType(userAgentInfo.getDeviceType())
                .hostAddress(userAgentInfo.getHostAddress())
                .hostName(userAgentInfo.getHostName())
                .createdOn(LocalDateTime.now())
                .build();
        pengunjungRepository.save(pengunjung);
        response.addCookie(cookie);
    }

    @Override
    public String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            String hostName = request.getRemoteHost();
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            } else if (hostName != null && !hostName.isEmpty() && !"unknown".equalsIgnoreCase(hostName)) {
                return hostName;
            }
        }
        return request.getRemoteAddr();
    }

    @Override
    public void populateIndexModel(Model model) {
        long totAgenda = agendaRepository.count();
        long totFiles = filesRepository.count();
        long totSiswa = siswaRepository.count();
        long totGuru = guruRepository.count();
        List<Tulisan> tulisanList = tulisanRepository.findTop4();
        List<Pengumuman> pengumuman = pengumumanRepository.findTop4();
        List<Agenda> agenda = agendaRepository.findTop4();

        model.addAttribute("tulisanList", tulisanList);
        model.addAttribute("pengumuman", pengumuman);
        model.addAttribute("agenda", agenda);
        model.addAttribute("totGuru", totGuru);
        model.addAttribute("totAgenda", totAgenda);
        model.addAttribute("totFiles", totFiles);
        model.addAttribute("totSiswa", totSiswa);
    }

    @Override
    public void populateAboutModel(Model model) {
        long totAgenda = agendaRepository.count();
        long totFiles = filesRepository.count();
        long totSiswa = siswaRepository.count();
        long totGuru = guruRepository.count();

        model.addAttribute("totGuru", totGuru);
        model.addAttribute("totAgenda", totAgenda);
        model.addAttribute("totFiles", totFiles);
        model.addAttribute("totSiswa", totSiswa);
    }

    @Override
    public void populateGuruModel(Model model) {
        List<Guru> guruList = guruRepository.findAll();
        model.addAttribute("guruList", guruList);
    }

    @Override
    public void populateSiswaModel(Model model) {
        List<Siswa> siswaList = siswaRepository.findAll();
        model.addAttribute("siswaList", siswaList);
    }

    @Override
    public void populateBlogModel(Model model) {
        List<Tulisan> tulisanList = tulisanRepository.findAll();
        List<Kategori> kategoriList = kategoriRepository.findAll();
        model.addAttribute("tulisanList", tulisanList);
        model.addAttribute("kategoriList", kategoriList);
    }

    @Override
    public void populateBlogDetailsModel(Model model, String slug) {
        String[] colors = {"#ff9e67", "#10bdff", "#14b5c7", "#f98182", "#8f9ce2", "#ee2b33", "#d4ec15", "#613021"};
        Tulisan tulisan = tulisanRepository.findBySlug(slug);
        List<Tulisan> populer = tulisanRepository.findByOrderByViewsDesc();
        List<Kategori> kategoriList = kategoriRepository.findAll();
        List<Komentar> komentarList = komentarRepository.findByTulisanIdAndStatusAndParent(tulisan.getId(), "1", 0);
        List<Komentar> parentKomentarList = komentarRepository.findByStatusAndParentOrderByCreatedOnAsc("1", 0);
        model.addAttribute("tulisan", tulisan);
        model.addAttribute("populer", populer);
        model.addAttribute("kategoriList", kategoriList);
        model.addAttribute("komentarList", komentarList);
        model.addAttribute("parentKomentarList", parentKomentarList);
        model.addAttribute("colors", colors);
    }

    @Override
    public void populatePengumumanModel(Model model) {
        List<Pengumuman> pengumumanList = pengumumanRepository.findByOrderByCreatedOnDesc();
        model.addAttribute("pengumumanList", pengumumanList);
    }

    @Override
    public void populateAgendaModel(Model model) {
        List<Agenda> agendaList = agendaRepository.findAll();
        model.addAttribute("agendaList", agendaList);
    }

    @Override
    public void populateDownloadModel(Model model) {
        List<Files> filesList = filesRepository.findAll();
        model.addAttribute("filesList", filesList);
    }

    @Override
    public void populateGaleriModel(Model model) {
        List<Galeri> galeriList = galeriRepository.findAll();
        model.addAttribute("galeriList", galeriList);
    }

    @Override
    public void populateTestUserAgentModel(Model model, HttpServletRequest request) {
        String remoteIpAddr = "";
        String remoteHostAddr = "";
        if (request != null) {
            remoteIpAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteIpAddr == null || remoteIpAddr.isEmpty()) {
                remoteIpAddr = request.getRemoteAddr();
                remoteHostAddr = request.getRemoteHost();
            }
        }

        String clientIP = getClientIpAddress(request);
        String clientHost = Objects.requireNonNull(request).getRemoteHost();
        UserAgentInfo userAgentInfo = WebUtils.showUserAgentInfo(parser.parse(request.getHeader("User-Agent")));
        userAgentInfo.setHostAddress(remoteIpAddr);
        userAgentInfo.setHostName(remoteHostAddr);

        log.info("clientIP: {}", clientIP);
        log.info("clientHost: {}", clientHost);
        model.addAttribute("userAgentInfo", userAgentInfo);
        model.addAttribute("waktu", LocalDateTime.now());
    }
}