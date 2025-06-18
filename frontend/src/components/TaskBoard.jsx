import { useEffect, useState } from 'react';
import axios from 'axios';

function TaskBoard() {
  const [tarefas, setTarefas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingTarefa, setEditingTarefa] = useState(null);
  const [showEditModal, setShowEditModal] = useState(false);
  const [formData, setFormData] = useState({
    description: '',
    sectorName: '',
    priority: 'Média',
    userId: ''
  });

  useEffect(() => {
    fetchTarefas();
  }, []);

  const fetchTarefas = async () => {
    try {
      setLoading(true);
      const response = await axios.get('http://localhost:8080/api/task');
      setTarefas(response.data);
      setLoading(false);
    } catch (err) {
      setError("Erro ao carregar tarefas");
      setLoading(false);
      console.error("Erro:", err);
    }
  };

  const abrirEdicao = (tarefa) => {
    setEditingTarefa(tarefa);
    setFormData({
      description: tarefa.description,
      sectorName: tarefa.sectorName,
      priority: tarefa.priority,
      userId: tarefa.userId ?? (tarefa.user?.id ?? '')
    });
    setShowEditModal(true);
  };

  const fecharModal = () => {
    setShowEditModal(false);
    setEditingTarefa(null);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const salvarEdicao = async () => {
    try {
      await axios.put(`http://localhost:8080/api/task`, {
        id: editingTarefa.id,
        description: formData.description,
        sectorName: formData.sectorName,
        priority: formData.priority,
        status: editingTarefa.status,
        user: formData.userId ? { id: parseInt(formData.userId) } : null,
        dateRegister: editingTarefa.dateRegister
      });
      fetchTarefas();
      fecharModal();
    } catch (err) {
      console.error("Erro ao atualizar tarefa:", err);
      alert("Erro ao atualizar tarefa");
    }
  };

  const moverTarefa = async (id, novoStatus) => {
    try {
      await axios.patch(`http://localhost:8080/api/task/${id}/status`, null, {
        params: { status: novoStatus }
      });
      fetchTarefas();
    } catch (err) {
      console.error("Erro ao mover tarefa:", err);
    }
  };

  const excluirTarefa = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir esta tarefa?')) {
      try {
        await axios.delete(`http://localhost:8080/api/task/${id}`);
        fetchTarefas();
      } catch (err) {
        console.error("Erro ao excluir tarefa:", err);
      }
    }
  };

  const colunas = [
    { id: 'A Fazer', titulo: 'A Fazer' },
    { id: 'Fazendo', titulo: 'Fazendo' },
    { id: 'Pronto', titulo: 'Pronto' }
  ];

  if (loading) return <div>Carregando tarefas...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="p-8">
      <h3 className="text-lg font-bold mb-4">Gerenciamento de Tarefas</h3>
      <h4 className="font-bold mb-4">Tarefas</h4>

      {showEditModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white p-6 rounded-lg w-full max-w-md">
            <h3 className="text-lg font-bold mb-4">Editar Tarefa</h3>

            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Descrição</label>
                <input
                  type="text"
                  name="description"
                  value={formData.description}
                  onChange={handleInputChange}
                  className="w-full p-2 border rounded"
                />
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">Setor</label>
                <input
                  type="text"
                  name="sectorName"
                  value={formData.sectorName}
                  onChange={handleInputChange}
                  className="w-full p-2 border rounded"
                />
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">Prioridade</label>
                <select
                  name="priority"
                  value={formData.priority}
                  onChange={handleInputChange}
                  className="w-full p-2 border rounded"
                >
                  <option value="Baixa">Baixa</option>
                  <option value="Média">Média</option>
                  <option value="Alta">Alta</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">ID do Usuário</label>
                <input
                  type="number"
                  name="userId"
                  value={formData.userId}
                  onChange={handleInputChange}
                  className="w-full p-2 border rounded"
                  placeholder="Ex: 5"
                />
              </div>
            </div>

            <div className="flex justify-end space-x-2 mt-6">
              <button onClick={fecharModal} className="px-4 py-2 border rounded">Cancelar</button>
              <button onClick={salvarEdicao} className="px-4 py-2 bg-blue-600 text-white rounded">Salvar</button>
            </div>
          </div>
        </div>
      )}

      <div className="flex gap-6">
        {colunas.map((coluna) => (
          <div key={coluna.id} className="flex-1 border rounded p-4">
            <h5 className="font-bold mb-4">{coluna.titulo}</h5>

            {tarefas
              .filter(tarefa => tarefa.status === coluna.id)
              .map(tarefa => (
                <div key={tarefa.id} className="bg-white p-4 rounded shadow mb-4">
                  <p><strong>Descrição:</strong> {tarefa.description}</p>
                  <p><strong>Setor:</strong> {tarefa.sectorName}</p>
                  <p><strong>Prioridade:</strong> {tarefa.priority}</p>

                  <div className="mt-4 flex justify-between">
                    <div className="space-x-2">
                      <button
                        className="bg-blue-600 text-white px-3 py-1 rounded text-sm"
                        onClick={() => moverTarefa(
                          tarefa.id,
                          coluna.id === 'A Fazer' ? 'Fazendo' :
                            coluna.id === 'Fazendo' ? 'Pronto' : 'A Fazer'
                        )}
                      >
                        {coluna.id === 'A Fazer' ? 'Iniciar' :
                          coluna.id === 'Fazendo' ? 'Concluir' : 'Reabrir'}
                      </button>
                      <button
                        className="bg-gray-600 text-white px-3 py-1 rounded text-sm"
                        onClick={() => abrirEdicao(tarefa)}
                      >
                        Editar
                      </button>
                    </div>
                    <button
                      className="bg-red-600 text-white px-3 py-1 rounded text-sm"
                      onClick={() => excluirTarefa(tarefa.id)}
                    >
                      Excluir
                    </button>
                  </div>
                </div>
              ))}
          </div>
        ))}
      </div>
    </div>
  );
}

export default TaskBoard;
