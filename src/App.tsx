import React from 'react';
import RecipeMain from './components/RecipeMain';
import {BrowserRouter} from 'react-router-dom';

const App = () => {
  return (
      <BrowserRouter>
        <RecipeMain />
      </BrowserRouter>
  );
};

export default App;
