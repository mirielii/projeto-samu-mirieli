import java.awt.Color;

/**
 * Unidade de Suporte Básico (USB). Atende apenas chamados de gravidade
 * LEVE ou MODERADO.
 *
 * Ciclo de um atendimento:
 *  1. livre (sem destino) -> a central chama setLocalDeBusca();
 *  2. desloca-se até o paciente -> ao chegar, avisa central.chegouAoLocal();
 *  3. a central chama embarca() -> desloca-se até o hospital;
 *  4. ao chegar, avisa central.chegouAoHospital() -> a central chama
 *     desembarcaPaciente().
 */
public class AmbulanciaBasica implements Ator, ItemDesenhavel
{
    private static final Color COR_LIVRE = new Color(80, 140, 255);
    private static final Color COR_OCUPADA = new Color(0, 40, 160);

    private CentralSamu central;
    private Localizacao localizacao;
    private Localizacao destino;
    private Paciente paciente;
    private int etapasOciosas;
    private int atendimentosRealizados;

    public AmbulanciaBasica(CentralSamu central, Localizacao localizacao)
    {
        this.central = central;
        this.localizacao = localizacao;
        destino = null;
        paciente = null;
        etapasOciosas = 0;
        atendimentosRealizados = 0;
    }

    /**
     * Comportamento em uma etapa: se tem destino, avança uma célula em
     * direção a ele e avisa a central ao chegar; se não tem destino, apenas
     * contabiliza uma etapa ociosa.
     */
    public void age()
{
    if(destino != null) {
        localizacao = localizacao.proximaLocalizacao(destino);
        if(localizacao.equals(destino)) {
            if(paciente == null) {
                central.chegouAoLocal(this);
            }
            else {
                central.chegouAoHospital(this, paciente);
            }
        }
    }
    else {
        etapasOciosas++;
    }
}

    /**
     * @return true se a ambulância pode atender um chamado com esta gravidade.
     */
    public boolean podeAtender(int gravidade)
{
    return gravidade == Gravidade.LEVE
        || gravidade == Gravidade.MODERADO;
}

    public boolean estaLivre()
    {
        return destino == null && paciente == null;
    }

    public void setLocalDeBusca(Localizacao local)
    {
        destino = local;
    }

    public void embarca(Paciente novoPaciente, Localizacao localHospital)
    {
        paciente = novoPaciente;
        destino = localHospital;
    }

    public void desembarcaPaciente()
    {
        paciente = null;
        destino = null;
        atendimentosRealizados++;
    }

    public Localizacao getLocalizacao()
    {
        return localizacao;
    }

    public Localizacao getDestino()
    {
        return destino;
    }

    public Paciente getPaciente()
    {
        return paciente;
    }

    public int getEtapasOciosas()
    {
        return etapasOciosas;
    }

    public int getAtendimentosRealizados()
    {
        return atendimentosRealizados;
    }

    public Color getCor()
    {
        if(paciente == null) {
            return COR_LIVRE;
        }
        return COR_OCUPADA;
    }

    public String toString()
    {
        return "Ambulância Básica em " + localizacao;
    }
}
