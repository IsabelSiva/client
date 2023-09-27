package com.isabelSilva.client.services;

import com.isabelSilva.client.dto.ClientDTO;
import com.isabelSilva.client.entities.Client;
import com.isabelSilva.client.repositories.ClientRepository;
import com.isabelSilva.client.services.exceptions.DatabaseException;
import com.isabelSilva.client.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(PageRequest pageRequest) {
        Page<Client> categoryList = repository.findAll(pageRequest);
        return categoryList.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = this.repository.getById(id);
        ClientDTO clientDTO = new ClientDTO(client);
        return clientDTO;
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = new Client();
        copyDtoToEntity(dto, client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    private void copyDtoToEntity(ClientDTO dto, Client client) {
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
        client.setBirthDate(dto.getBirthDate());
        client.setChildren(dto.getChildren());
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) throws ResourceNotFoundException {
        try {
            Client client = repository.getReferenceById(id);
            copyDtoToEntity(dto, client);
            client = repository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not Found" + id);
        }
    }

    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not Found" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
