package br.com.compass.site.services;

import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;
import br.com.compass.site.dto.cartoes.response.ResponseCartoesDto;
import br.com.compass.site.entities.CartoesEntity;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.exceptions.CartaoNaoVinculado;
import br.com.compass.site.repository.CartoesRepository;
import br.com.compass.site.repository.ClienteRepository;
import br.com.compass.site.util.ValidaCartoes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartoesService {
    private final CartoesRepository cartoesRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ValidaCartoes validaCartoes;


    public ResponseCartoesDto vinculaCartao(String cpf, RequestCartoesDto requestDto) {
        validaCartoes.ValidaCartao(requestDto);

        ClienteEntity clienteEntity = clienteRepository.findById(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CartoesEntity cartoesEntity = modelMapper.map(requestDto, CartoesEntity.class);
        clienteEntity.getCartoes().add(cartoesEntity);
        clienteRepository.save(clienteEntity);

        int ultimoCartao = clienteEntity.getCartoes().size() - 1;
        return modelMapper.map(clienteEntity.getCartoes().get(ultimoCartao), ResponseCartoesDto.class);
    }

    public List<ResponseCartoesDto> mostraCartoesDoCliente(String cpf) {
        ClienteEntity clienteEntity = clienteRepository.findById(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return clienteEntity.getCartoes().stream().map(cartoesEntity -> modelMapper.map(cartoesEntity, ResponseCartoesDto.class)).collect(Collectors.toList());
    }

    public ResponseCartoesDto mostraCartaoDoClienteUnitario(String cpf, Long id) {
        CartoesEntity cartoesEntity = cartoesRepository.findByIdAndCliente_cpf(id, cpf).orElseThrow(CartaoNaoVinculado::new);
        return modelMapper.map(cartoesEntity, ResponseCartoesDto.class);
    }

    public void atualizaCartao(String cpf, Long id, RequestCartoesDto requestDto) {
        CartoesEntity cartoesEntity = cartoesRepository.findByIdAndCliente_cpf(id, cpf).orElseThrow(CartaoNaoVinculado::new);

        validaCartoes.ValidaCartao(requestDto);
        modelMapper.map(requestDto, cartoesEntity);
        cartoesRepository.save(cartoesEntity);
    }
}
