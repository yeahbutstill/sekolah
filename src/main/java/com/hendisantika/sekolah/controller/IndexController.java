package com.hendisantika.sekolah.controller;

import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.service.IndexService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/03/20
 * Time: 21.05
 */
@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "HOME");
        log.info("Menampilkan data untuk Halaman Home.");
        indexService.populateIndexModel(model);
        return "/index";
    }

    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "ABOUT");
        log.info("Menampilkan data untuk Halaman about.");
        indexService.populateAboutModel(model);
        return "/about";
    }

    @GetMapping("/guru")
    public String showGuru(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "GURU");
        log.info("Menampilkan data untuk Halaman Guru.");
        indexService.populateGuruModel(model);
        return "/guru";
    }

    @GetMapping("/siswa")
    public String showSiswa(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "SISWA");
        log.info("Menampilkan data untuk Halaman Siswa.");
        indexService.populateSiswaModel(model);
        return "/siswa";
    }

    @GetMapping("/blog")
    public String showBlog(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "BLOG");
        log.info("Menampilkan data untuk Halaman Blog.");
        indexService.populateBlogModel(model);
        return "/blog";
    }

    @GetMapping("/artikel/{slug}")
    public String showBlogDetails(Model model, @PathVariable(value = "slug") String slug, HttpServletRequest request,
                                  HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "ARTIKEL");
        log.info("Menampilkan data untuk Halaman Details Blog.");
        indexService.populateBlogDetailsModel(model, slug);
        return "/artikel";
    }

    @GetMapping("/pengumuman")
    public String showPengumuman(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "PENGUMUMAN");
        log.info("Menampilkan data untuk Halaman Pengumuman.");
        indexService.populatePengumumanModel(model);
        return "/pengumuman";
    }

    @GetMapping("/agenda")
    public String showAgenda(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "AGENDA");
        log.info("Menampilkan data untuk Halaman Agenda.");
        indexService.populateAgendaModel(model);
        return "/agenda";
    }

    @GetMapping("/download")
    public String showDownload(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "DOWNLOAD");
        log.info("Menampilkan data untuk Halaman Download.");
        indexService.populateDownloadModel(model);
        return "/download";
    }

    @GetMapping("/galeri")
    public String showGaleri(Model model, HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "GALERI");
        log.info("Menampilkan data untuk Halaman Galeri.");
        indexService.populateGaleriModel(model);
        return "/galeri";
    }

    @GetMapping("/contact")
    public String showContact(HttpServletRequest request, HttpServletResponse response) {
        indexService.createCookieAndSave(request, response, "CONTACT");
        log.info("Menampilkan data untuk Halaman Contact Us.");
        return "/contact";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        log.info("Menampilkan data untuk Halaman Login.");
        model.addAttribute("user", new Pengguna());
        return "/admin/login";
    }

    @GetMapping("/test")
    public String getUserAgent2(Model model, HttpServletRequest request) {
        log.info("Menampilkan data untuk Halaman Test User Agent.");
        indexService.populateTestUserAgentModel(model, request);
        return "/samples/userAgent";
    }
}