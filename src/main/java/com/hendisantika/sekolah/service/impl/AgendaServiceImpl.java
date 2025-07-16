package com.hendisantika.sekolah.service.impl;

import com.hendisantika.sekolah.entity.Agenda;
import com.hendisantika.sekolah.repository.AgendaRepository;
import com.hendisantika.sekolah.service.AgendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.AGENDA;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    @Override
    public void populateAgendaListModel(Model model, Pageable pageable) {
        model.addAttribute("agendaList", agendaRepository.findAll(pageable));
        model.addAttribute("waktu", LocalDateTime.now());
    }

    @Override
    public void populateAgendaFormModel(Model model) {
        model.addAttribute(AGENDA.getDescription(), new Agenda());
    }

    @Override
    public void saveAgenda(Agenda agenda, String createdBy) {
        agenda.setCreatedBy(createdBy);
        agendaRepository.save(agenda);
    }

    @Override
    public void populateEditAgendaFormModel(Model model, UUID agendaId) {
        model.addAttribute(AGENDA.getDescription(), agendaRepository.findById(agendaId));
    }

    @Override
    public void updateAgenda(Agenda agendaBaru) {
        Agenda agenda = agendaRepository.findById(agendaBaru.getId())
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found with id: " + agendaBaru.getId()));
        agenda.setNama(agendaBaru.getNama());
        agenda.setDeskripsi(agendaBaru.getDeskripsi());
        agenda.setMulai(agendaBaru.getMulai());
        agenda.setTempat(agendaBaru.getTempat());
        agendaRepository.save(agenda);
    }

    @Override
    public void deleteAgenda(UUID agendaId) {
        agendaRepository.deleteById(agendaId);
    }
}