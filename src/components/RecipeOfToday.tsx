import React, {useState, useEffect} from 'react';
import Recipe from './Recipe';

interface RecipeOfTodayProps {
    recipesOfToday: Recipe[];
}

interface ImageObject {
    id: number;
    url: string;
    label: string
}

interface LabelRange {
    label: string;
    startHour: number;
    endHour: number;
}

const labelRange: LabelRange[] = [
    {label: "Breakfast", startHour: 6, endHour: 10},
    {label: "Lunch", startHour: 12, endHour: 14},
    {label: "Dinner", startHour: 16, endHour: 21}
];

const RecipeOfToday: React.FC<RecipeOfTodayProps> = (props) => {
    const {recipesOfToday} = props;
    const images: ImageObject[] = recipesOfToday.map((recipe: Recipe, index: number): ImageObject => {
            return {
                id: recipe.id,
                url: recipe.image,
                label: labelRange[index]?.label ?? "Snack"
            }
    });

    // get the image based on time
    const getImageByTime = (): ImageObject => {
        const currentHour = new Date().getHours();
        const currentLabelObj = labelRange.find(time => currentHour >= time.startHour && currentHour < time.endHour);
        return currentLabelObj ? images[labelRange.indexOf(currentLabelObj)] : images[images.length - 1];
    }

    const [mainImage, setMainImage] = useState(getImageByTime);
    const handleThumbnailClick = (image: ImageObject) => setMainImage(image);
    // update main image every minute to check for time changes
    useEffect( () => {
        const interval = setInterval( () => {
            setMainImage(getImageByTime());
        }, 60000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div className="recipe-of-today">
            <h2>Today's Recipes</h2>
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
                        <div key={image.id} className="thumbnail-wrapper">
                            <img
                                key={image.id}
                                src={image.url}
                                alt={`Recipe ${image.id}`}
                                className="random-recipe-image"
                                onClick={() => handleThumbnailClick(image)}
                            />
                            <div className="meal-label">{image.label}</div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}


export default RecipeOfToday;