import java.util.Random;

/**
 * Gera aleatoriamente os chamados de emergência da cidade.
 */
public class FonteChamados implements Ator
{
    public static final double PROBABILIDADE_CHAMADO = 0.30;
    public static final double PROPORCAO_LEVE = 0.50;
    public static final double PROPORCAO_MODERADO = 0.35;

    private final Cidade cidade;
    private final CentralSamu central;
    private final Random rand;
    private int totalGerados;

    public FonteChamados(Cidade cidade, CentralSamu central)
    {
        this.cidade = cidade;
        this.central = central;
        this.rand = Randomizador.getRandom();
        this.totalGerados = 0;
    }

    /**
     * Possivelmente gera um novo chamado e o encaminha à central.
     */
    public void age()
    {
        if(rand.nextDouble() < PROBABILIDADE_CHAMADO) {
            Paciente paciente = criaPaciente();
            cidade.adicionaItem(paciente);
            central.solicitaAtendimento(paciente);
            totalGerados++;
        }
    }

    private Paciente criaPaciente()
    {
        Localizacao local = new Localizacao(
            rand.nextInt(cidade.getLargura()),
            rand.nextInt(cidade.getAltura())
        );

        return new Paciente(local, sorteiaGravidade(), central.getEtapaAtual());
    }

    /**
     * Sorteia a gravidade de um chamado conforme as proporções definidas.
     */
    private int sorteiaGravidade()
    {
        double sorteio = rand.nextDouble();

        if(sorteio < PROPORCAO_LEVE) {
            return Gravidade.LEVE;
        }
        else if(sorteio < PROPORCAO_LEVE + PROPORCAO_MODERADO) {
            return Gravidade.MODERADO;
        }

        return Gravidade.GRAVE;
    }

    public int getTotalGerados()
    {
        return totalGerados;
    }
}