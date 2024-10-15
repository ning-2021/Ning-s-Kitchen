import React from 'react';
import Recipe from './Recipe';

interface RecipeOfTodayProps {
    recipesOfToday: Recipe[];
}

interface ImageObject {
    id: number;
    url: string;
}

const RecipeOfToday: React.FC<RecipeOfTodayProps> = (props) => {
    const {recipesOfToday} = props;
    const images: ImageObject[] = recipesOfToday.map((recipe: Recipe): ImageObject => {
            return {
                id: recipe.id,
                url: recipe.image
            }
    });

    return (
        <div className="recipe-of-today">
            <h2>Recipe Of Today</h2>
            <div className="image-container">
                {images.map((image: ImageObject) => (
                    <img
                        key={image.id}
                        src={image.url}
                        alt={`Recipe ${image.id}`}
                        className="recipe-image"
                    />
                ))}
            </div>
        </div>
    )
}


export default RecipeOfToday;