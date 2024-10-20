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
            return {
                allRecipes: responseAllRecipes.data,
                randomRecipes: randomRecipeIdsOfTheTagIdArray
            };
        } catch (err) {
          throw new Error((err as Error).message);
        }
    }

    useEffect(() => {
        const updateRecipes = async () => {
            const now = new Date();
            const localStorageKey = 'selectedRecipes';
            const storedData = JSON.parse(localStorage.getItem(localStorageKey) || '{}');
            const lastUpdate = new Date(storedData.timestamp || 0);
            console.log("last update time: " + lastUpdate);
            console.log("current time: " + now);
            console.log("last update date: " + lastUpdate.getDate());
            console.log("current date: " + now.getDate());
            console.log("last update hour: " + lastUpdate.getHours());
            console.log("current hour: " + now.getHours());

            try {
                const { allRecipes, randomRecipes } = await fetchRecipes();
                // update random recipes selection at 6am every day
                if ((now.getDate() !== lastUpdate.getDate() && now.getHours() >= 6) ||
                    (now.getDate() === lastUpdate.getDate() && now.getHours() >= 6 && lastUpdate.getHours() < 6)) {
                    const { allRecipes, randomRecipes } = await fetchRecipes();
                    // store the new recipes and the current timestamp
                    localStorage.setItem(localStorageKey, JSON.stringify({
                        timestamp: now.toISOString(),
                        randomRecipes
                    }));
                }
                setRecipeState(prevState => ({
                    ...prevState,
                    allRecipes,
                    randomRecipes
                }));
            } catch (err) {
                setError((err as Error).message);
            } finally {
                setLoading(false);
            }
        };
        updateRecipes();
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
