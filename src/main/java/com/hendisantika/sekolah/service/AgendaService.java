package com.hendisantika.sekolah.service;

import com.hendisantika.sekolah.entity.Agenda;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.UUID;

public interface AgendaService {
    void populateAgendaListModel(Model model, Pageable pageable);

    void populateAgendaFormModel(Model model);

    void saveAgenda(Agenda agenda, String createdBy);

    void populateEditAgendaFormModel(Model model, UUID agendaId);

    void updateAgenda(Agenda agendaBaru);

    void deleteAgenda(UUID agendaId);
}
