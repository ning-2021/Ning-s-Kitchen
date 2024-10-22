import React, {useState} from 'react';
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

    const [mainImage, setMainImage] = useState(images[0]);
    const handleThumbnailClick = (image: ImageObject) => setMainImage(image);

    return (
        <div className="recipe-of-today">
            <h2>Recipe Of Today</h2>
            <div className="display-container">
                <div className="main-image-container">
                    <img
                        src={mainImage.url}
                        alt={`Recipe ${mainImage.id}`}
                        className="main-display-image"
                    />
                </div>
                <div className="thumbnail-image-container">
                    {images.map((image: ImageObject) => (
                        <img
                            key={image.id}
                            src={image.url}
                            alt={`Recipe ${image.id}`}
                            className="random-recipe-image"
                            onClick={() => handleThumbnailClick(image)}
                        />
                    ))}
                </div>
            </div>
        </div>
    )
}


export default RecipeOfToday;