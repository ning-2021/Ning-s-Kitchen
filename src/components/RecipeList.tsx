import React from 'react';
import Recipe from './Recipe';
import RecipeCard from './RecipeCard';

interface RecipeListProps {
  title: string;
  recipes: Recipe[];
}

const RecipeList: React.FC<RecipeListProps> = (props) => {
  const {title, recipes} = props;
  return (
    <div className="recipe-list">
      <h2>{title}</h2>
      {recipes.map((recipe) => (
        <RecipeCard recipe={recipe} />
      ))}
    </div>
  );
};

export default RecipeList;
