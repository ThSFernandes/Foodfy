package br.foodfy.order.service;


import br.foodfy.order.model.ItemPedido;
import br.foodfy.order.model.MenuItemResponse;
import br.foodfy.order.model.MenuResponse;
import br.foodfy.order.model.Pedido;
import br.foodfy.order.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

	@Autowired
    private PedidoRepository pedidoRepository;
    
    public PedidoService() {
    	
    }

    public Pedido criarPedido(Pedido pedido) {
        // Validar pedido
        validarPedido(pedido);

        // Calcular total do pedido
        double total = calcularTotal(pedido);
        pedido.setTotal(total);

        // Salvar pedido
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            System.out.println("Pedido com ID " + id + " não encontrado.");
        }
        return pedido;
    }


    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    private void validarPedido(Pedido pedido) {
        // Obter o menu específico para o pedido
        String urlMenu = "http://menu-service/api/menus/" + pedido.getId();
        RestTemplate restTemplate = new RestTemplate();
        MenuResponse menuResponse = restTemplate.getForObject(urlMenu, MenuResponse.class);

        if (menuResponse == null) {
            throw new RuntimeException("Menu não encontrado para o pedido: " + pedido.getId());
        }

        // Converter a lista de itens do menu em um mapa para acesso rápido
        Map<Long, MenuItemResponse> menuMap = menuResponse.getItems().stream()
                .collect(Collectors.toMap(MenuItemResponse::getId, item -> item));

        // Validar cada item do pedido
        for (ItemPedido itemPedido : pedido.getItens()) {
            MenuItemResponse menuItem = menuMap.get(itemPedido.getId());
            if (menuItem == null) {
                throw new RuntimeException("Item não encontrado no menu: " + itemPedido.getId());
            }
            if (itemPedido.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade inválida para o item: " + itemPedido.getId());
            }
            // Adicionar outras validações, como verificar se o item está em estoque, se necessário
        }
    }


    private double calcularTotal(Pedido pedido) {
        double total = 0;
        for (ItemPedido item : pedido.getItens()) {
            total += item.getPreco() * item.getQuantidade();
        }
        // Aplicar taxas ou descontos se necessário
        return total;
    }

    public void atualizarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoRepository.save(pedido);
        }
    }
}