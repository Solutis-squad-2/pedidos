package pedidos.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pedidos.demo.dto.PedidosDTO;
import pedidos.demo.model.Pedido;
import pedidos.demo.model.PedidoCredito;
import pedidos.demo.model.PedidoDebito;
import pedidos.demo.model.PedidoPix;
import pedidos.demo.repository.PedidosRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<List<PedidosDTO>> buscaPorCPF(String cpf) {
        List<Pedido> resultado = pedidosRepository.findByCpf(cpf);

        return Optional.of(resultado.stream()
                .map(pedido -> new PedidosDTO(pedido))
                .collect(Collectors.toList()));
    }

    public PedidosDTO cadastrar(PedidosDTO dto) {
        Pedido pedido;


        switch (dto.getFormaDePagamento()){
            case CREDITO:
                pedido = new PedidoCredito(
                        dto.getNumeroCartao(),
                        dto.getCodigoCartao(),
                        dto.getParcelas());
                break;
            case DEBITO:
                pedido = new PedidoDebito(
                        dto.getNumeroCartao(),
                        dto.getCodigoCartao());
                break;
            case PIX:
                pedido = new PedidoPix();
                break;
            default:
                throw new IllegalArgumentException("Forma de pagamento não reconhecida");
        }
        // Mapeia os demais campos

        modelMapper.map(dto, pedido);
        pedido = pedidosRepository.save(pedido);

        PedidosDTO respostaDTO = modelMapper.map(pedido, PedidosDTO.class);

        if (pedido instanceof PedidoPix) {
            respostaDTO.setChavePix(((PedidoPix) pedido).getChavePix());
        }

        return respostaDTO;
    }

    public Optional<PedidosDTO> buscaPorId(long id) {

        Optional<Pedido> busca = pedidosRepository.findById(id);

        return busca.map(pedido -> modelMapper.map(pedido, PedidosDTO.class));
        //return Optional.ofNullable(modelMapper.map(busca, PedidosDTO.class));
    }

    public void deletarPorId(Long id) {

            pedidosRepository.deleteById(id);
       }

    public List<PedidosDTO> buscarPedidos() {
        List<Pedido> pedidos = pedidosRepository.findAll();

        return pedidos.stream()
                .map(pedido -> new PedidosDTO(pedido))
                .collect(Collectors.toList());
    }
}
