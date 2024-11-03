package pedidos.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.Pedido;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidosDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private FormaDePagamento formaDePagamento;
    private String numeroCartao;
    private String codigoCartao;
    private int parcelas;
    private String descricaoPedido;
    private BigDecimal valor;
    private String chavePix;

    public PedidosDTO(Long id, String nome, String cpf, String email, String telefone, FormaDePagamento formaDePagamento, String numeroCartao, String codigoCartao, int parcelas, String descricaoPedido, BigDecimal valor, String chavePix) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.formaDePagamento = formaDePagamento;
        this.numeroCartao = numeroCartao;
        this.codigoCartao = codigoCartao;
        this.parcelas = parcelas;
        this.descricaoPedido = descricaoPedido;
        this.valor = valor;
        this.chavePix = chavePix;
    }

    public PedidosDTO() {
    }

    public PedidosDTO(Pedido pedido) {
        if (pedido != null) {
            this.id = pedido.getId();
            this.nome = pedido.getNome();
            this.cpf = pedido.getCpf();
            this.email = pedido.getEmail();
            this.formaDePagamento = pedido.getFormaDePagamento();
            this.numeroCartao = numeroCartao;
            this.codigoCartao = codigoCartao;
            this.parcelas = parcelas;
            this.telefone = pedido.getTelefone();
            this.descricaoPedido = pedido.getDescricaoPedido();
            this.valor = pedido.getValor();
        }
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }
}