/**
 * Interface implementada por todos os participantes ativos da simulação
 * (central, fonte de chamados, veículos, hospitais e interface gráfica).
 * A cada etapa da simulação, o método age() de cada ator é chamado uma vez.
 */
public interface Ator
{
    /**
     * Executa o comportamento do ator durante uma etapa da simulação.
     */
    void age();
}
