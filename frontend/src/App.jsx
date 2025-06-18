import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import UserForm from './components/UserForm.jsx';
import TaskForm from './components/TaskForm.jsx';
import TaskBoard from './components/TaskBoard.jsx';
// Aqui você pode importar outros componentes, como CadastroTarefa, etc.

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/usuarios" element={<UserForm />} />
        <Route path="/tarefas" element={<TaskForm />} />
        <Route path="/gerenciar" element={<TaskBoard />} />
      </Routes>
    </Router>
  );
}

export default App;
