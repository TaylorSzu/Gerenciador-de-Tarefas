// src/components/TaskForm.js
import { useState, useEffect } from 'react';
import axios from 'axios';

function TaskForm() {
  const [description, setDescription] = useState('');
  const [sectorName, setSectorName] = useState('');
  const [userId, setUserId] = useState('');
  const [priority, setPriority] = useState('Baixa');
  const [usuarios, setUsuarios] = useState([]);
  const [mensagem, setMensagem] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8080/api/user')
      .then(res => {
        setUsuarios(res.data);
        console.log('Usuários recebidos:', res.data);
      })
      .catch(() => setUsuarios([]));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/task', {
        description,
        sectorName,
        priority,
        user: {
          id: parseInt(userId)  // Certifique-se de enviar um número
        }
      });
      setMensagem('Tarefa cadastrada com sucesso!');
      setDescription('');
      setSectorName('');
      setUserId('');
      setPriority('Baixa');
    } catch (error) {
      console.error('Erro ao cadastrar tarefa:', error);
      setMensagem('Erro ao cadastrar tarefa.');
    }
  };

  return (
    <div className="flex">
      <main className="flex-1 p-8">
        <h1 className="text-xl font-semibold mb-6">Cadastro de Tarefas</h1>
        <form onSubmit={handleSubmit} className="max-w-md space-y-4">
          <div>
            <label className="block mb-1 font-medium">Descrição:</label>
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
              className="w-full border px-3 py-2 rounded"
            />
          </div>
          <div>
            <label className="block mb-1 font-medium">Setor:</label>
            <input
              type="text"
              value={sectorName}
              onChange={(e) => setSectorName(e.target.value)}
              required
              className="w-full border px-3 py-2 rounded"
            />
          </div>
          <div>
            <label className="block mb-1 font-medium">Usuário:</label>
            <select
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              className="w-full border px-3 py-2 rounded"
              required
            >
              <option value="">Selecione</option>
              {usuarios.map((u) => (
                <option key={u.id} value={u.id}>{u.nome}</option>
              ))}
            </select>
          </div>
          <div>
            <label className="block mb-1 font-medium">Prioridade:</label>
            <select
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
              className="w-full border px-3 py-2 rounded"
            >
              <option>Baixa</option>
              <option>Média</option>
              <option>Alta</option>
            </select>
          </div>
          <button
            type="submit"
            className="bg-[rgb(0,86,179)] text-white px-4 py-2 rounded hover:bg-[rgb(0,86,255)]"
          >
            Cadastrar
          </button>
          {mensagem && <p className="text-green-600 mt-2">{mensagem}</p>}
        </form>
      </main>
    </div>
  );
}

export default TaskForm;
