import React from 'react';
import {Recipe} from './types';
import RecipeCard from './RecipeCard';
import '../App.css';

interface RecipeListProps {
  title: string;
  recipes: Recipe[];
}

const RecipeList: React.FC<RecipeListProps> = (props) => {
  const {title, recipes} = props;
    // chunk recipes array into groups of 3
    const chunkArray = (arr: Recipe[], size: number) => {
        return Array.from({length: Math.ceil(arr.length/size)}, (_,i) => arr.slice(i * size, (i + 1) * size));
    };

    const rows = chunkArray(recipes, 3);

    return (
        <div className="recipe-list">
        <h2>{title}</h2>
        {rows.map((row, rowIndex) => (
            <div key={rowIndex} className="recipe-row">
                {row.map((recipe) => (
                    <RecipeCard recipe={recipe} />
                ))}
            </div>
        ))}
        </div>
    )

}

export default RecipeList;
