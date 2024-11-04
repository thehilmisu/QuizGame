import logo from './logo.svg';

import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import QuestionList from './components/QuestionList';

function App() {
    return (
        <div className="App">
            <QuestionList />
        </div>
    );
}

export default App;

