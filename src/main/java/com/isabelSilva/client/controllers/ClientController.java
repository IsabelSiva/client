package com.isabelSilva.client.controllers;

import com.isabelSilva.client.dto.ClientDTO;
import com.isabelSilva.client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public List<ClientDTO> findAll(){
        return this.service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ClientDTO findOne(@PathVariable Long id){
        return this.service.findOne(id);
    }

}
