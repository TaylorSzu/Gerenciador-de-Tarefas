import { Link } from 'react-router-dom';

function Navbar() {

  return (
    <div className="flex justify-between p-5 text-white bg-[rgb(0,86,179)]">
        <h1 className="font-semibold">Gerenciamento de Tarefas</h1>
        <nav className="space-x-4 text-sm">
        <ul className="flex space-y-4 text-sm">
            <li className='mr-4'><Link to="/usuarios" className="hover:underline">Cadastro de Usuários</Link></li>
            <li className='mr-4'><Link to="/tarefas" className="hover:underline">Cadastro de Tarefas</Link></li>
            <li className='mr-4'><Link to="/gerenciar" className="hover:underline">Gerenciar Tarefa</Link></li>
      </ul>
        </nav>
    </div>
  );
}

export default Navbar;
