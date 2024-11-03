import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { NavItem, DropdownItem } from './types';
import dotenv from 'dotenv';
import axios from 'axios';

const Navbar: React.FC = () => {
    const [navItems, setNavItems] = useState<NavItem[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [activeDropdown, setActiveDropdown] = useState<number | null>(null);

    useEffect(() => {
        const fetchNavItems = async() => {
            try {
                const response = await axios.get<NavItem[]>(`http://${process.env.HOST}:${process.env.PORT}/navbarItems`);
                // transform the API data to match the NavItem interface
                const transformedNavItems: NavItem[] = response.data.map(item => ({
                    id: item.id,
                    name: item.name,
                    href: `/${item.name.toLowerCase().replace(/\s+/g, '-')}`,
                }));
                setNavItems(transformedNavItems);
            } catch (err) {
                setError('Failed to fetch navigation data');
            } finally {
                setIsLoading(false);
            }
        }
        fetchNavItems();
    }, []);

    if (isLoading) return <div>Loading.......</div>;
    if (error) return <div>{error}</div>;

    return (
        <nav className="navbar">
            <ul className="navbar-links">
                {navItems.map((item, index) => (
                    <li key={index}>
                        <Link to={item.href}>{item.name}</Link>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;

