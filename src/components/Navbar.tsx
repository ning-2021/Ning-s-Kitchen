import React, { useState, useEffect } from 'react';
import { NavItem, DropdownItem } from './types';
import { Link } from 'react-router-dom';
import dotenv from 'dotenv';
import axios from 'axios';
import Dropdown from './Dropdown';

interface NavProps {
    navItems: NavItem[];
    selectedTags: number[];
    setSelectedTags: React.Dispatch<React.SetStateAction<number[]>>;
    toggleTagSelection: (id: number) => void
}

const Navbar: React.FC<NavProps> = (props) => {
    const {navItems, selectedTags, setSelectedTags, toggleTagSelection} = props;
    const [error, setError] = useState<string | null>(null);
    const [activeDropdown, setActiveDropdown] = useState<number | null>(null);

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
                        <Dropdown
                            navItem={navItem}
                            activeDropdown={activeDropdown}
                            selectedTags={selectedTags}
                            setSelectedTags={setSelectedTags}
                            toggleTagSelection={toggleTagSelection}
                        />
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;

