package br.com.compass.site.services;

import br.com.compass.site.dto.cliente.request.RequestClienteDto;
import br.com.compass.site.dto.cliente.request.RequestPutClienteDto;
import br.com.compass.site.dto.cliente.response.ResponseClienteDto;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;


    public ResponseClienteDto criaCliente(RequestClienteDto requestDto) {
        if (clienteRepository.existsById(requestDto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
        }
        ClienteEntity clienteEntity = modelMapper.map(requestDto, ClienteEntity.class);
        ClienteEntity saveEntity = clienteRepository.save(clienteEntity);
        return modelMapper.map(saveEntity, ResponseClienteDto.class);
    }

    public List<ResponseClienteDto> listaClientes() {
        List<ClienteEntity> clienteEntityList = clienteRepository.findAll();
        return clienteEntityList.stream().map(clienteEntity -> modelMapper.map(clienteEntity, ResponseClienteDto.class)).collect(Collectors.toList());
    }

    public ResponseClienteDto listaCliente(String cpf) {
        ClienteEntity clienteEntity = clienteRepository.findById(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(clienteEntity, ResponseClienteDto.class);
    }


    public void atualizaCliente(String cpf, RequestPutClienteDto requestDto) {
        ClienteEntity clienteEntity = clienteRepository.findById(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(requestDto, clienteEntity);
        clienteRepository.save(clienteEntity);
    }
}
