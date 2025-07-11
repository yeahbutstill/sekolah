package com.hendisantika.sekolah.service.impl;

import static com.hendisantika.sekolah.util.WebUtils.getUserAgent;
import static com.hendisantika.sekolah.util.WebUtils.showUserAgentInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.hendisantika.sekolah.dto.UserAgentInfo;
import com.hendisantika.sekolah.entity.Agenda;
import com.hendisantika.sekolah.entity.Files;
import com.hendisantika.sekolah.entity.Galeri;
import com.hendisantika.sekolah.entity.Guru;
import com.hendisantika.sekolah.entity.Kategori;
import com.hendisantika.sekolah.entity.Komentar;
import com.hendisantika.sekolah.entity.Pengumuman;
import com.hendisantika.sekolah.entity.Pengunjung;
import com.hendisantika.sekolah.entity.Siswa;
import com.hendisantika.sekolah.entity.Tulisan;
import com.hendisantika.sekolah.repository.AgendaRepository;
import com.hendisantika.sekolah.repository.FilesRepository;
import com.hendisantika.sekolah.repository.GaleriRepository;
import com.hendisantika.sekolah.repository.GuruRepository;
import com.hendisantika.sekolah.repository.KategoriRepository;
import com.hendisantika.sekolah.repository.KomentarRepository;
import com.hendisantika.sekolah.repository.PengumumanRepository;
import com.hendisantika.sekolah.repository.PengunjungRepository;
import com.hendisantika.sekolah.repository.SiswaRepository;
import com.hendisantika.sekolah.repository.TulisanRepository;
import com.hendisantika.sekolah.service.IndexService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

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

  public static String getClientIpAddress(HttpServletRequest request) {
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
  public void createAndSaveVisitorCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
    String remoteIpAddr = "";
    String remoteHostAddr = "";
    if (request != null) {
      remoteIpAddr = request.getHeader("X-FORWARDED-FOR");
      if (remoteIpAddr == null || remoteIpAddr.isEmpty()) {
        remoteIpAddr = request.getRemoteAddr();
        remoteHostAddr = request.getRemoteHost();
      }
    }
    log.info("Membuat cookie untuk pelacakan pengunjung.");
    Cookie cookie = new Cookie(cookieName, RequestContextHolder.currentRequestAttributes().getSessionId());
    cookie.setMaxAge(cookieMaxAge);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setPath("/"); // cookie global yang dapat diakses di mana saja

    UserAgentInfo userAgentInfo = showUserAgentInfo(
        parser.parse(Objects.requireNonNull(request).getHeader("User-Agent")));
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
  public Map<String, Object> getHomeDashboardData() {
    log.info("Mengambil data untuk Halaman Beranda.");
    Map<String, Object> data = new HashMap<>();
    data.put("tulisanList", tulisanRepository.findTop4());
    data.put("pengumuman", pengumumanRepository.findTop4());
    data.put("agenda", agendaRepository.findTop4());
    data.put("totGuru", guruRepository.count());
    data.put("totAgenda", agendaRepository.count());
    data.put("totFiles", filesRepository.count());
    data.put("totSiswa", siswaRepository.count());
    return data;
  }

  @Override
  public Map<String, Object> getAboutPageData() {
    log.info("Mengambil data untuk Halaman Tentang Kami.");
    Map<String, Object> data = new HashMap<>();
    data.put("totGuru", guruRepository.count());
    data.put("totAgenda", agendaRepository.count());
    data.put("totFiles", filesRepository.count());
    data.put("totSiswa", siswaRepository.count());
    return data;
  }

  @Override
  public List<Guru> getAllGuru() {
    log.info("Mengambil semua data Guru.");
    return guruRepository.findAll();
  }

  @Override
  public List<Siswa> getAllSiswa() {
    log.info("Mengambil semua data Siswa.");
    return siswaRepository.findAll();
  }

  @Override
  public Map<String, Object> getBlogPageData() {
    log.info("Mengambil data untuk Halaman Blog.");
    Map<String, Object> data = new HashMap<>();
    data.put("tulisanList", tulisanRepository.findAll());
    data.put("kategoriList", kategoriRepository.findAll());
    return data;
  }

  @Override
  public Map<String, Object> getArticleDetails(String slug) {
    log.info("Mengambil data untuk Halaman Detail Artikel untuk slug: {}", slug);
    Map<String, Object> data = new HashMap<>();
    Tulisan tulisan = tulisanRepository.findBySlug(slug);
    List<Tulisan> popular = tulisanRepository.findByOrderByViewsDesc();
    List<Kategori> kategoriList = kategoriRepository.findAll();
    List<Komentar> komentarList = komentarRepository
        .findByTulisanIdAndStatusAndParent(tulisan.getId(), "1", 0);
    List<Komentar> parentKomentarList = komentarRepository
        .findByStatusAndParentOrderByCreatedOnAsc("1", 0);

    String[] colors = { "#ff9e67", "#10bdff", "#14b5c7", "#f98182", "#8f9ce2", "#ee2b33", "#d4ec15", "#613021" };

    data.put("tulisan", tulisan);
    data.put("popular", popular);
    data.put("kategoriList", kategoriList);
    data.put("komentarList", komentarList);
    data.put("parentKomentarList", parentKomentarList);
    data.put("colors", colors);
    return data;
  }

  @Override
  public List<Pengumuman> getAllPengumuman() {
    log.info("Mengambil semua data Pengumuman.");
    return pengumumanRepository.findByOrderByCreatedOnDesc();
  }

  @Override
  public List<Agenda> getAllAgenda() {
    log.info("Mengambil semua data Agenda.");
    return agendaRepository.findAll();
  }

  @Override
  public List<Files> getAllFiles() {
    log.info("Mengambil semua data File.");
    return filesRepository.findAll();
  }

  @Override
  public List<Galeri> getAllGaleri() {
    log.info("Mengambil semua data Galeri.");
    return galeriRepository.findAll();
  }

  @Override
  public UserAgentInfo getUserAgentInformation(HttpServletRequest request) {
    String remoteIpAddr = "";
    String remoteHostAddr = "";
    if (request != null) {
      remoteIpAddr = request.getHeader("X-FORWARDED-FOR");
      if (remoteIpAddr == null || remoteIpAddr.isEmpty()) {
        remoteIpAddr = request.getRemoteAddr();
        remoteHostAddr = request.getRemoteHost();
      }
    }

    UserAgentInfo userAgentInfo = getUserAgent(request);
    UserAgentInfo userAgentInfo2 = showUserAgentInfo(parser.parse(userAgentInfo.getUserAgent()));
    userAgentInfo2.setHostAddress(remoteIpAddr);
    userAgentInfo2.setHostName(remoteHostAddr);
    return userAgentInfo2;
  }
}
