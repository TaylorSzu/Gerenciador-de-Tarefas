import { useState } from 'react';
import axios from 'axios';

function UserForm() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [mensagem, setMensagem] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/user', { nome, email });
      setMensagem('Usuário cadastrado com sucesso!');
      setNome('');
      setEmail('');
    } catch (error) {
      setMensagem('Erro ao cadastrar. Verifique os campos.');
    }
  };

  return (
    <div className="flex">
      {/* Conteúdo principal */}
      <main className="flex-1 p-8">
        <h1 className="text-xl font-semibold mb-6">Cadastro de Usuários</h1>

        <form onSubmit={handleSubmit} className="max-w-md space-y-4">
          <div>
            <label className="block mb-1 font-medium">Nome:</label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
              className="w-full border border-gray-300 rounded px-3 py-2"
            />
          </div>
          <div>
            <label className="block mb-1 font-medium">Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full border border-gray-300 rounded px-3 py-2"
            />
          </div>
          <button
            type="submit"
            className="bg-[rgb(0,86,179)] text-white px-4 py-2 rounded hover:bg-[rgb(0,86,255)]"
          >
            Cadastrar
          </button>
          {mensagem && <p className="text-green-600 font-medium mt-2">{mensagem}</p>}
        </form>
      </main>
    </div>
  );
}

export default UserForm;
