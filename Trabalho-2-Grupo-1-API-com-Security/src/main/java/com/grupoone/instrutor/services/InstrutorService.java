package com.grupoone.instrutor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoone.instrutor.entities.Instrutor;
import com.grupoone.instrutor.exceptions.NoSuchElementException;
import com.grupoone.instrutor.repositories.InstrutorRepository;

@Service
public class InstrutorService {

	@Autowired
	InstrutorRepository instrutorRepository;
	
	@Autowired
	EmailService emailService;

	public List<Instrutor> getAllInstrutores() {
		return instrutorRepository.findAll();
	}

	public Instrutor getInstrutorById(Integer id) {
		return instrutorRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
	}

	public Instrutor saveInstrutor(Instrutor instrutor) {
		Instrutor novoInstrutor = instrutorRepository.save(instrutor);
		emailService.enviarEmail("email@outlook.com", "Instrutor cadastrado", novoInstrutor.toString());
		return novoInstrutor;
	}	

	public Instrutor updateInstrutor(Instrutor instrutor, Integer id) {
		return instrutorRepository.save(instrutor);
	}

	public Boolean deleteInstrutor(Integer id) {
		Instrutor instrutorDeleted = instrutorRepository.findById(id).orElse(null);
		if (instrutorDeleted != null) {
			instrutorRepository.deleteById(id);
			instrutorDeleted = instrutorRepository.findById(id).orElse(null);
			if (instrutorDeleted != null) {
				return false;
			} else {
				return true;
			}
			
		} else {
			return false;
		}
	}

}
