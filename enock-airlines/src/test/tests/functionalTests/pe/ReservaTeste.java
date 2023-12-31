package functionalTests.pe;

import main.Passageiro;
import main.Reserva;
import main.Voo;
import main.VooRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservaTeste {

    private Reserva reserva;
    private final String CONFIRMACAO =
    "RESERVA CONFIRMADA\n" +
    "Origem: B | Destino: B | Data: 20/08/2023 | Horário: 10:00 | Preço: R$200,00\n" +
    "Cliente: Jão | Email: jao@mail.com | Telefone: 4002-8922";

    public void construirReservaVazia() {
        this.reserva = new Reserva();
    }

    public void construirReservaCom3Voos() {
        construirReservaVazia();

        Voo voo1 = new Voo("Origem A", "Destino A", "20/08/2023", "10:00", 200.0);
        Voo voo2 = new Voo("Origem B", "Destino B", "20/08/2023", "10:00", 200.0);
        Voo voo3 = new Voo("Origem C", "Destino C", "20/08/2023", "10:00", 200.0);

        this.reserva.getVooRepository().adicionarVoo(voo1);
        this.reserva.getVooRepository().adicionarVoo(voo2);
        this.reserva.getVooRepository().adicionarVoo(voo3);
    }

    @AfterEach
    public void destruir() {
        this.reserva = null;
    }

    @Test
    public void CT1ReservarVoo() {
        construirReservaCom3Voos();

        Passageiro passageiro = new Passageiro("Jão", "jao@mail.com", "senha123", "4002-8922");

        String retorno = this.reserva.reservarVoo(0, 1, passageiro);

        VooRepository vooRepository = this.reserva.getVooRepository();
        List<Voo> voos = vooRepository.getVoos();
        Voo vooReservado = voos.get(0);

        assertEquals(29, vooReservado.getVagasAtuais());
        assertEquals(CONFIRMACAO, retorno);
    }

    @Test
    public void CT2CancelarVoo() {
        construirReservaCom3Voos();

        Passageiro passageiro = new Passageiro("Jão", "jao@mail.com", "senha123", "4002-8922");

        this.reserva.reservarVoo(1, 5, passageiro);

        this.reserva.cancelarReserva(1, 1, "jao@mail.com", "senha123");

        assertEquals(26, this.reserva.getVooRepository().getVoos().get(1).getVagasAtuais());

    }
}