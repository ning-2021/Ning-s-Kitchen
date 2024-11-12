import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { NavItem, DropdownItem } from './types';
import dotenv from 'dotenv';
import axios from 'axios';

const Navbar: React.FC = () => {
    const [navItems, setNavItems] = useState<NavItem[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [activeDropdown, setActiveDropdown] = useState<number | null>(null);

    const fetchNavItems = async() => {
        try {
            const response = await axios.get<NavItem[]>(`http://${process.env.HOST}:${process.env.PORT}/navbarItems`);
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

    const handleMouseEnter = (navItemId: number) => {
        setActiveDropdown(navItemId);
    };

    const handleMouseLeave = () => {
        setActiveDropdown(null);
    };

    return (
        <nav className="navbar">
            <ul className="navbar-links">
                <Link to={"/"}>Home</Link>
                {navItems.map((item) => (
                    <li key={item.id} onMouseEnter={() => handleMouseEnter(item.id)} onMouseLeave={handleMouseLeave}>
                        <Link to={item.href}>{item.name}</Link>
                        {activeDropdown === item.id && item.dropdownItems && item.dropdownItems.length > 0 && (
                            <ul className="dropdown">
                                {item.dropdownItems.map((dropdownItem) => (
                                    <li key={dropdownItem.id}>
                                        <Link to={dropdownItem.href}>{dropdownItem.name}</Link>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;

