/**
 * Lançada quando um veículo chega a um local de busca e a central não
 * encontra nenhum paciente designado para ele.
 */
public class PacienteAusenteException extends RuntimeException
{
    private Object veiculo;

    public PacienteAusenteException(Object veiculo)
    {
        super("Nenhum paciente designado para " + veiculo);
    }

    public Object getVeiculo()
    {
        return veiculo;
    }
}
