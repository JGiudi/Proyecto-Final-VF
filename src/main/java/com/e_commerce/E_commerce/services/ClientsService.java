package com.e_commerce.E_commerce.services;

import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setName(clientDetails.getName());
            client.setSurname(clientDetails.getSurname());
            client.setEmail(clientDetails.getEmail());
            client.setDni(clientDetails.getDni());
            client.setPoints(clientDetails.getPoints());
            return clientRepository.save(client);
        }).orElse(null);
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
