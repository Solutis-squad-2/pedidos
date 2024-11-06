package pedidos.demo.controller;

import jakarta.transaction.Transactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedidos.demo.dto.PedidosDTO;
import pedidos.demo.service.PedidosService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("pedidos")
public class PedidosController {


    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> buscarPedidos(){
        List<PedidosDTO> resultado = pedidosService.buscarPedidos();

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/cpf")
    public ResponseEntity<Optional<List<PedidosDTO>>> buscarPedidoPorCpf(@RequestBody Map<String, String> body){

       Optional<List<PedidosDTO>> dtoList = pedidosService.buscaPorCPF(body.get("cpf"));

        if(dtoList.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(dtoList);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PedidosDTO> cadastrar(@RequestBody PedidosDTO dto){
        PedidosDTO pedido = pedidosService.cadastrar(dto);

/*
        Message message = new Message (("Pedido criado com o id: " + pedido.getId()).getBytes());
        rabbitTemplate.send("pedido.cadastro", message);
*/

        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidosDTO> buscaPedidoPorId(@PathVariable long id){

        Optional<PedidosDTO> pedido = pedidosService.buscaPorId(id);
        if(pedido.isEmpty()){
            throw new RuntimeException("Esse ID não foi encontrado no banco de dados");
        }else {
            return pedido.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @Transactional
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarPedidoPorId(@PathVariable Long id){

       Optional<PedidosDTO> pedido = pedidosService.buscaPorId(id);

       if(pedido.isEmpty()){
           throw new RuntimeException("Esse ID não foi encontrado no banco de dados");
       }else {
           pedidosService.deletarPorId(id);
           return ResponseEntity.noContent().build();
       }
    }

/*
    @PatchMapping("atualizar/{id}")
    public ResponseEntity atualizarPedido(@PathVariable Long id){

    }
    */
}
