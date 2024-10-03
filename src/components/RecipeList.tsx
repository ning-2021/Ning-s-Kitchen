import React from 'react';

interface Recipe {
  id: number;
  title: string;
  description: string;
  instructions: string;
  rating: number;
  image: string;
  duration: number;
  created_at: string
}

interface RecipeListProps {
  title: string;  // The section title like "Featured Recipes"
  recipes: Recipe[];  // The list of recipes to display
}

const RecipeList: React.FC<RecipeListProps> = ({ title, recipes }) => {
  return (
    <div className="recipe-list">
      <h2>{title}</h2> {/* Display the section title */}
      {recipes.map((recipe) => (
        <div key={recipe.id} className="recipe-card">
          <img src={recipe.image} alt={recipe.title} />
          <h3>{recipe.title}</h3>
          <p>{recipe.description}</p>
        </div>
      ))}
    </div>
  );
};

export default RecipeList;
