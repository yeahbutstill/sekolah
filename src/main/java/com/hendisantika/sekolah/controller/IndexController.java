package com.hendisantika.sekolah.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.service.IndexService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {

  private final IndexService indexService;

  @GetMapping
  public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "HOME");
    log.info("Menampilkan data untuk Halaman Beranda.");
    Map<String, Object> data = indexService.getHomeDashboardData();
    model.addAllAttributes(data);
    return "/index";
  }

  @GetMapping("/about")
  public String about(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "ABOUT");
    log.info("Menampilkan data untuk Halaman Tentang Kami.");
    Map<String, Object> data = indexService.getAboutPageData();
    model.addAllAttributes(data);
    return "/about";
  }

  @GetMapping("/guru")
  public String showGuru(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "GURU");
    log.info("Menampilkan data untuk Halaman Guru.");
    model.addAttribute("guruList", indexService.getAllGuru());
    return "/guru";
  }

  @GetMapping("/siswa")
  public String showSiswa(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "SISWA");
    log.info("Menampilkan data untuk Halaman Siswa.");
    model.addAttribute("siswaList", indexService.getAllSiswa());
    return "/siswa";
  }

  @GetMapping("/blog")
  public String showBlog(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "BLOG");
    log.info("Menampilkan data untuk Halaman Blog.");
    Map<String, Object> data = indexService.getBlogPageData();
    model.addAllAttributes(data);
    return "/blog";
  }

  @GetMapping("/artikel/{slug}")
  public String showBlogDetails(Model model, @PathVariable(value = "slug") String slug, HttpServletRequest request,
      HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "ARTIKEL");
    log.info("Menampilkan data untuk Halaman Detail Blog.");
    Map<String, Object> data = indexService.getArticleDetails(slug);
    model.addAllAttributes(data);
    return "/artikel";
  }

  @GetMapping("/pengumuman")
  public String showPengumuman(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "PENGUMUMAN");
    log.info("Menampilkan data untuk Halaman Pengumuman.");
    model.addAttribute("pengumumanList", indexService.getAllPengumuman());
    return "/pengumuman";
  }

  @GetMapping("/agenda")
  public String showAgenda(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "AGENDA");
    log.info("Menampilkan data untuk Halaman Agenda.");
    model.addAttribute("agendaList", indexService.getAllAgenda());
    return "/agenda";
  }

  @GetMapping("/download")
  public String showDownload(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "DOWNLOAD");
    log.info("Menampilkan data untuk Halaman Unduh.");
    model.addAttribute("filesList", indexService.getAllFiles());
    return "/download";
  }

  @GetMapping("/galeri")
  public String showGaleri(Model model, HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "GALERI");
    log.info("Menampilkan data untuk Halaman Galeri.");
    model.addAttribute("galeriList", indexService.getAllGaleri());
    return "/galeri";
  }

  @GetMapping("/contact")
  public String showContact(HttpServletRequest request, HttpServletResponse response) {
    indexService.createAndSaveVisitorCookie(request, response, "CONTACT");
    log.info("Menampilkan data untuk Halaman Kontak Kami.");
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
    model.addAttribute("userAgentInfo", indexService.getUserAgentInformation(request));
    model.addAttribute("waktu", LocalDateTime.now());
    return "/samples/userAgent";
  }
}
