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

    // fetch all recipes and recipes for "recipes of today" section
    const fetchRecipes = async () => {
        try {
            const responseAllRecipes = await axios.get<Recipe[]>(`http://${process.env.HOST}:${process.env.PORT}/recipes`);
            const responseRecipesOfToDay = await axios.get<Recipe[]>(`http://${process.env.HOST}:${process.env.PORT}/today-recipes`);
            return {
                allRecipes: responseAllRecipes.data,
                randomRecipes: responseRecipesOfToDay.data
            };
        } catch (err) {
          throw new Error((err as Error).message);
        }
    }

    useEffect(() => {
        const updateRecipes = async () => {
            try {
                const { allRecipes, randomRecipes } = await fetchRecipes();
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
