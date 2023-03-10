import { useState } from 'react';
import reactLogo from './assets/react.svg';
import './App.css';
import {
  createBrowserRouter,
  Route,
  RouterProvider,
  Routes,
} from 'react-router-dom';
import Nav from './Nav';
import StartMatch from './pages/StartMatch';
import Live from './pages/Live';
import Home from './pages/Home';
import Delete from './pages/Delete';
function App() {
  return (
    <>
      <Nav />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/playmatch' element={<StartMatch />} />
        <Route path='/live' element={<Live />} />
        <Route path='/delete' element={<Delete />} />
        <Route path='*' element={<Home />} />
      </Routes>
    </>
  );
}

export default App;
