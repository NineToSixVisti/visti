import React, { createContext, useContext, useState } from "react";

type SelectedImageContextType = {
  selectedImage: File | null;
  setSelectedImage: React.Dispatch<React.SetStateAction<File | null>>;
};

export const SelectedImageContext = createContext<SelectedImageContextType | undefined>(undefined);

interface SelectedImageProviderProps {
    children: React.ReactNode;
  }
  
  export const SelectedImageProvider: React.FC<React.PropsWithChildren<{}>> = ({ children }) => {
    const [selectedImage, setSelectedImage] = useState<File | null>(null);
  
    return (
      <SelectedImageContext.Provider value={{ selectedImage, setSelectedImage }}>
        {children}
      </SelectedImageContext.Provider>
    );
  };
export const useSelectedImage = () => {
  const context = useContext(SelectedImageContext);
  if (!context) {
    throw new Error('useSelectedImage must be used within a SelectedImageProvider');
  }
  return context;
};
export default SelectedImageContext; 