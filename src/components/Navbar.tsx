import React, { useState, useEffect } from 'react';
import { NavItem, DropdownItem } from './types';
import { Link } from 'react-router-dom';
import dotenv from 'dotenv';
import axios from 'axios';
import Dropdown from './Dropdown';

const Navbar: React.FC = () => {
    const [navItems, setNavItems] = useState<NavItem[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [activeDropdown, setActiveDropdown] = useState<number | null>(null);

    const fetchNavItems = async() => {
        try {
            const response = await axios.get<NavItem[]>('/api/navbarItems');
            // transform the API data to match the NavItem interface
            const transformedNavItems: NavItem[] = response.data.map(item => {
                const parentHref = `/${item.name.toLowerCase().replace(/\s+/g, '-')}`;
                return {
                    id: item.id,
                    name: item.name,
                    href: parentHref,
                    dropdownItems: item.dropdownItems?.map(dropdownItem => ({
                        id: dropdownItem.id,
                        name: dropdownItem.name,
                        href: `${parentHref}/${dropdownItem.name.toLowerCase().replace(/\s+/g, '-')}`
                    })) ?? []
                };
            });
            setNavItems(transformedNavItems);
        } catch (err) {
            setError('Failed to fetch navigation data');
        }
    }

    useEffect(() => {
        fetchNavItems();
    }, []);

    const handleMouseEnter = (navItemId: number): void => {
        setActiveDropdown(navItemId);
    };

    const handleMouseLeave = (): void => {
        setActiveDropdown(null);
    }

    return (
        <nav className="navbar">
            <ul className="navbar-links">
                <Link to={"/"}>Home</Link>
                {navItems.map(navItem => (
                    <li key={navItem.id} onMouseOver={() => handleMouseEnter(navItem.id)} onMouseLeave={() => handleMouseLeave()}>
                        <Dropdown navItem={navItem} activeDropdown={activeDropdown}/>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;

