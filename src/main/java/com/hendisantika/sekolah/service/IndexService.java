package com.hendisantika.sekolah.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public interface IndexService {
    void createCookieAndSave(HttpServletRequest request, HttpServletResponse response, String cookieName);

    String getClientIpAddress(HttpServletRequest request);

    void populateIndexModel(Model model);

    void populateAboutModel(Model model);

    void populateGuruModel(Model model);

    void populateSiswaModel(Model model);

    void populateBlogModel(Model model);

    void populateBlogDetailsModel(Model model, String slug);

    void populatePengumumanModel(Model model);

    void populateAgendaModel(Model model);

    void populateDownloadModel(Model model);

    void populateGaleriModel(Model model);

    void populateTestUserAgentModel(Model model, HttpServletRequest request);
}
