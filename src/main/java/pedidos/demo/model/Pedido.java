package pedidos.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "forma_pagamento", discriminatorType = DiscriminatorType.STRING)
public abstract class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "É obrigatório ter um nome!")
    private String nome;

    @NotBlank(message = "O CPF deve conter 11 dígitos!")
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank(message = "A forma de pagamento é obrigatória")
    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;

    @Email
    private String email;
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Obrigatório colocar a descrição do pedido!")
    private String descricaoPedido;

    @NotNull
    @Positive
    private BigDecimal valor;


}
