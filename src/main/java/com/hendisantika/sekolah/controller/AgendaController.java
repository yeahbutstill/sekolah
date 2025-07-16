package com.hendisantika.sekolah.controller;

import com.hendisantika.sekolah.entity.Agenda;
import com.hendisantika.sekolah.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;
import java.util.UUID;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.RIE_ADMIN_AGENDA;


@Slf4j
@Controller
@RequestMapping("/admin/agenda")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping
    public String agenda(Model model, Pageable pageable) {
        log.info("Menampilkan data untuk Halaman List Agenda.");
        agendaService.populateAgendaListModel(model, pageable);
        return "/admin/agenda/agenda-list";
    }

    @GetMapping("/add")
    public String tampilkanFormAgenda(Model model) {
        log.info("Menampilkan Form Agenda");
        agendaService.populateAgendaFormModel(model);
        return "/admin/agenda/agenda-form";
    }

    @PostMapping
    public String tambahAgenda(@Valid Agenda agenda, Principal principal, BindingResult errors,
                               SessionStatus status) {
        log.info("Menambahkan Agenda baru");
        if (errors.hasErrors()) {
            return "/admin/agenda";
        }
        agendaService.saveAgenda(agenda, principal.getName());
        status.setComplete();
        log.info("Data agenda yang baru {}", agenda);
        return RIE_ADMIN_AGENDA.getDescription();
    }

    @GetMapping("/edit/{agendaId}")
    public String tampilkanFormEditAgenda(@PathVariable("agendaId") UUID agendaId, Model model) {
        log.info("Menampilkan Form Edit Agenda");
        agendaService.populateEditAgendaFormModel(model, agendaId);
        return "/admin/agenda/agenda-edit";
    }

    @PostMapping("/edit")
    public String updateAgenda(Model model, @Valid Agenda agendaBaru, Pageable pageable) {
        log.info("Memperbaharui Data Agenda");
        agendaService.updateAgenda(agendaBaru);
        agendaService.populateAgendaListModel(model, pageable);
        return RIE_ADMIN_AGENDA.getDescription();
    }

    @GetMapping("/delete/{agendaId}")
    public String deleteAgenda(@PathVariable("agendaId") UUID agendaId, Model model, Pageable pageable) {
        log.info("Hapus data Agenda");
        agendaService.deleteAgenda(agendaId);
        agendaService.populateAgendaListModel(model, pageable);
        return RIE_ADMIN_AGENDA.getDescription();
    }
}