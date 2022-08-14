package br.com.compass.site.services;

import br.com.compass.site.repository.CartoesRepository;
import br.com.compass.site.repository.ClienteRepository;
import br.com.compass.site.repository.ItemRepository;
import br.com.compass.site.util.ControleDeEstoque;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CheckoutService.class)
class CheckoutServiceTest {

    @Autowired
    private CheckoutService checkoutService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CartoesRepository cartoesRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ControleDeEstoque controleDeEstoque;

    @SpyBean
    private ModelMapper modelMapper;

    @Test
    void enviaPedido() {


    }
}