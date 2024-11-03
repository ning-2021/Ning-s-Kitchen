import React from 'react';
import {Recipe} from './types';

const RecipeCard: React.FC<{recipe: Recipe}> = ({recipe}) => (
    <div key={recipe.id} className="recipe-card">
         <img src={recipe.image} alt={recipe.title} className="recipe-image"/>
         <h3>{recipe.title}</h3>
         <p>{recipe.description}</p>
    </div>
)

export default RecipeCard;

