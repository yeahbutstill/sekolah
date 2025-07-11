package com.hendisantika.sekolah.service;

import java.util.List;
import java.util.Map;

import com.hendisantika.sekolah.dto.UserAgentInfo;
import com.hendisantika.sekolah.entity.Agenda;
import com.hendisantika.sekolah.entity.Files;
import com.hendisantika.sekolah.entity.Galeri;
import com.hendisantika.sekolah.entity.Guru;
import com.hendisantika.sekolah.entity.Pengumuman;
import com.hendisantika.sekolah.entity.Siswa;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IndexService {

  void createAndSaveVisitorCookie(HttpServletRequest request, HttpServletResponse response, String cookieName);

  Map<String, Object> getHomeDashboardData();

  Map<String, Object> getAboutPageData();

  List<Guru> getAllGuru();

  List<Siswa> getAllSiswa();

  Map<String, Object> getBlogPageData();

  Map<String, Object> getArticleDetails(String slug);

  List<Pengumuman> getAllPengumuman();

  List<Agenda> getAllAgenda();

  List<Files> getAllFiles();

  List<Galeri> getAllGaleri();

  UserAgentInfo getUserAgentInformation(HttpServletRequest request);
}
