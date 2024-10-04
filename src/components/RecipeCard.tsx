import React from 'react';
import Recipe from './Recipe';

const RecipeCard: React.FC<{recipe: Recipe}> = ({recipe}) => (
    <div key={recipe.id} className="recipe-card">
         <img src={recipe.image} alt={recipe.title} />
         <h3>{recipe.title}</h3>
         <p>{recipe.description}</p>
    </div>
)

export default RecipeCard;

