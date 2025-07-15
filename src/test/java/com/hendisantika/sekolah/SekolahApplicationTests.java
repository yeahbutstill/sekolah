package com.hendisantika.sekolah;

import com.hendisantika.sekolah.entity.Guru;
import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.entity.Role;
import com.hendisantika.sekolah.repository.GuruRepository;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import com.hendisantika.sekolah.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test-containers")
class SekolahApplicationTests {

    @Autowired
    private GuruRepository guruRepository;
    @Autowired
    private PenggunaRepository penggunaRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        guruRepository.deleteAll();
        penggunaRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void shouldCreateOnePerson() {
        final var guru = guruRepository.saveAll(List.of(new Guru("212", "Itadori Yuji", "L", "Tokyo", LocalDate.now(), "Dukun",
                "photo", "photoBase64", "filename", new byte[(int) "Itadori Yuji".length()]))
        );
        assertEquals(1, guru.size());
        final var person = guru.get(0);
        assertEquals("Itadori Yuji", person.getNama());
        assertEquals("212", person.getNip());
    }

    @Test
    void shouldCreateOneUser() {
        Pengguna pengguna = new Pengguna();
        Role role = new Role();

        role.setRole("ADMIN");
        roleRepository.save(role);
        System.out.println(role.getCreatedOn());
        System.out.println(role.getModifiedOn());

        pengguna.setFullname("Dani Setiawan");
        pengguna.setUsername("yeahbutstill");
        pengguna.setPassword("password123");
        penggunaRepository.save(pengguna);

        assertNotNull(pengguna);
        assertNotNull(role);
    }

}
