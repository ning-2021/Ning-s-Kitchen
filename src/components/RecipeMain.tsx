import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import RecipeList from './RecipeList';
import Recipe from './Recipe';

const RecipeMain: React.FC = () => {
  const [allRecipes, setAllRecipes] = useState<Recipe[]>([]);
  const [randomRecipe, setRandomRecipe] = useState<Recipe | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const response = await axios.get<Recipe[]>('http://localhost:8080/recipes');
        setAllRecipes(response.data);

        // fetch recipe ids for the given tag id
        const tagId = 2;
        const responseOfRIdsOfTheTId = await axios.get<number[]>(`http://localhost:8080/recipes/ids?tag_id=${tagId}`);
        const rIdsOfTheTId = responseOfRIdsOfTheTId.data;
        if (rIdsOfTheTId.length === 0) {
            console.error('No recipes found for this tag.');
            return;
        }
        // pick a random recipe id
        const randomIndexByTId = Math.floor(Math.random() * rIdsOfTheTId.length);
        console.log(randomIndexByTId);
        const randomRIdByTId = rIdsOfTheTId[randomIndexByTId];
        // get this random picked recipe info
        const responseOfRandomRecipeByTId = await axios.get<Recipe>(`http://localhost:8080/recipes/${randomRIdByTId}`);
        setRandomRecipe(responseOfRandomRecipeByTId.data);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };
    fetchRecipes();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  const newestRecipes = [...allRecipes].sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime()).slice(0, 5);

  return (
    <div className="recipe-main">
      <RecipeList title="Newest Recipes" recipes={newestRecipes} />
    </div>
  );
};

export default RecipeMain;
