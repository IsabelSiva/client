package com.isabelSilva.client.services;

import com.isabelSilva.client.dto.ClientDTO;
import com.isabelSilva.client.entities.Client;
import com.isabelSilva.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll(){
        List<Client> clientList = this.repository.findAll();
        List<ClientDTO> dtoList = clientList.stream().map(e -> new ClientDTO(e)).collect(Collectors.toList());
        return dtoList;
    }

    @Transactional(readOnly = true)
    public ClientDTO findOne(Long id) {
        Client client = this.repository.getById(id);
        ClientDTO clientDTO = new ClientDTO(client);
        return  clientDTO;
    }
}
