import React, { useEffect, useState } from 'react';
import axios from 'axios';
import RecipeList from './RecipeList';
import RecipeOfToday from './RecipeOfToday';
import Recipe from './Recipe';
import dotenv from 'dotenv';

const RecipeMain: React.FC = () => {
  interface RecipeState {
    allRecipes: Recipe[];
    randomRecipes: Recipe[]
  }

  const [recipeState, setRecipeState] = useState<RecipeState>({
    allRecipes: [],
    randomRecipes: []
  });

  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const responseAllRecipes = await axios.get<Recipe[]>('http://localhost:8080/recipes');

        const tagIds = (process.env.TAG_IDS as string).split(",");
        const promiseArray_RecipeIdsOfTheTagId = tagIds.map(async (tagId) => {
            const responseOfRecipeIdsOfTheTagId = await axios.get<number[]>(`http://localhost:8080/recipes/ids?tag_id=${tagId}`);
            const recipeIdsOfTheTagId = responseOfRecipeIdsOfTheTagId.data;
            // pick a random recipe id and get its recipe info
            const randomRecipeIdByTagId = recipeIdsOfTheTagId[Math.floor(Math.random() * recipeIdsOfTheTagId.length)];
            const responseOfRandomRecipeByTagId = await axios.get<Recipe>(`http://localhost:8080/recipes/${randomRecipeIdByTagId}`);
            return responseOfRandomRecipeByTagId.data;
        });
        const randomRecipeIdsOfTheTagIdArray = await Promise.all(promiseArray_RecipeIdsOfTheTagId);

        setRecipeState(prevState => ({
            ...prevState,
            allRecipes: responseAllRecipes.data,
            randomRecipes: randomRecipeIdsOfTheTagIdArray})
        );

      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };
    fetchRecipes();
  }, []);

  const {allRecipes, randomRecipes} = recipeState;

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  const newestRecipes = [...allRecipes].sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime()).slice(0, 5);

  return (
    <div className="recipe-main">
      <RecipeOfToday recipesOfToday={randomRecipes} />
      <RecipeList title="Newest Recipes" recipes={newestRecipes} />
    </div>
  );
};

export default RecipeMain;
