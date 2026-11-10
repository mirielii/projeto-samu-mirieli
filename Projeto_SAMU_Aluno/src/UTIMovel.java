import java.awt.Color;

/**
 * Unidade de Suporte Avançado (UTI Móvel). Pode atender chamados de
 * qualquer gravidade, sendo a única capaz de atender casos GRAVES.
 *
 * Ciclo de um atendimento:
 *  1. livre (sem destino) -> a central chama setLocalDeBusca();
 *  2. desloca-se até o paciente -> ao chegar, avisa central.chegouAoLocal();
 *  3. a central chama embarca() -> desloca-se até o hospital;
 *  4. ao chegar, avisa central.chegouAoHospital() -> a central chama
 *     desembarcaPaciente().
 */
public class UTIMovel implements Ator, ItemDesenhavel
{
    private static final Color COR_LIVRE = new Color(200, 90, 230);
    private static final Color COR_OCUPADA = new Color(100, 0, 130);

    private CentralSamu central;
    private Localizacao localizacao;
    private Localizacao destino;
    private Paciente paciente;
    private int etapasOciosas;
    private int atendimentosRealizados;

    public UTIMovel(CentralSamu central, Localizacao localizacao)
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
        if(destino == null) {
            etapasOciosas++;
        }
        else {
            localizacao = localizacao.proximaLocalizacao(destino);
            if(localizacao.equals(destino)) {
                central.chegouAoLocal(this);
            }
        }
    }

    /**
     * @return true se a UTI pode atender um chamado com esta gravidade.
     */
    public boolean podeAtender(int gravidade)
    {
        return true;
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
        return "UTI Móvel em " + localizacao
    }
}
