import java.awt.Color;

/**
 * Representa uma pessoa que fez um chamado de emergência ao SAMU.
 * O paciente aguarda no local do chamado até ser buscado por um veículo
 * e então é levado a um hospital.
 */
public class Paciente implements ItemDesenhavel
{
    private Localizacao localizacao;
    private int gravidade;
    private double instanteChamado;

    /**
     * @param localizacao Local onde o paciente aguarda socorro.
     * @param gravidade Classificação de risco (Gravidade.LEVE, MODERADO ou GRAVE).
     * @param instanteChamado Etapa da simulação em que o chamado foi feito.
     */
    public Paciente(Localizacao localizacao, int gravidade, double instanteChamado)
    {
        if(localizacao == null) {
            throw new IllegalArgumentException("Localização do paciente nula.");
        }
        this.localizacao = localizacao;
        this.gravidade = gravidade;
        this.instanteChamado = instanteChamado;
    }

    /**
     * @return Quantas etapas se passaram desde o chamado.
     */
    public int getTempoDeEspera(int etapaAtual)
    {
        return (int) (etapaAtual - instanteChamado);
    }

    public Localizacao getLocalizacao()
    {
        return localizacao;
    }

    public Color getCor()
    {
        return Gravidade.getCor(gravidade);
    }

    public int getGravidade()
    {
        return gravidade;
    }

    public double getInstanteChamado()
    {
        return instanteChamado;
    }

    public String toString()
    {
        return "Paciente (" + Gravidade.getNome(gravidade) + ") em " + localizacao;
    }
}
