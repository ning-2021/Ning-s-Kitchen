import React, { useState, useCallback, useEffect } from 'react';
import { NavItem } from './types';
import { Link, useNavigate } from 'react-router-dom';

interface DropdownProps {
    navItem: NavItem;
    activeDropdown: number | null;
    selectedTags: number[];
    setSelectedTags: React.Dispatch<React.SetStateAction<number[]>>;
}

const Dropdown: React.FC<DropdownProps> = (props) => {
        const { navItem, activeDropdown, selectedTags, setSelectedTags } = props;

        const navigate = useNavigate();
        const [effectTrigger, setEffectTrigger] = useState<boolean>(false);

//         // Method 1:
//         const toggleOption = useCallback((id: number): void => {
//             console.log('Before update:', selectedTags);
//             setSelectedTags(prevSelected =>
//                 prevSelected.includes(id)
//                     ? prevSelected.filter(item => item !== id)
//                     : [...prevSelected, id]
//             );
//             setEffectTrigger(true);
//         }, [selectedTags]);
//
//         useEffect(() => {
//             const newPath = selectedTags.length > 0 ? `/?selected=${selectedTags.join('|')}` : '/';
//             navigate(newPath);
//             if (effectTrigger) {
//                 console.log('Actually updated to:', selectedTags);
//                 setEffectTrigger(false);
//             }
//         }, [effectTrigger]);

        // Method 2:
        const toggleOption = (id: number): void => {
            console.log('Before update:', selectedTags);
            setSelectedTags(prevSelected =>
                prevSelected.includes(id)
                    ? prevSelected.filter(item => item !== id)
                    : [...prevSelected, id]
            );
            setEffectTrigger(true);
        };

        useEffect(() => {
            if (effectTrigger) {
                const newPath = selectedTags.length > 0 ? `/?selected=${selectedTags.join('|')}` : '/';
                navigate(newPath);
                console.log('Actually updated to:', selectedTags);
                setEffectTrigger(false);
            }
        }, [effectTrigger]);

        return (
            <div>
                <Link to={navItem.href}>{navItem.name}</Link>
                {activeDropdown === navItem.id && navItem.dropdownItems && navItem.dropdownItems.length > 0 && (
                    <ul className="dropdown">
                        {navItem.dropdownItems.map(dropdownItem => {
                            const isSelected = selectedTags.includes(dropdownItem.id);
                            return (
                                <li key={dropdownItem.id} onClick={() => toggleOption(dropdownItem.id)}>
                                    <input type="checkbox" checked={isSelected} onChange={() => {}}/>
                                    <span>{dropdownItem.name}</span>
                                </li>
                            );
                        })}
                    </ul>
                )}
            </div>);
    }

export default Dropdown;