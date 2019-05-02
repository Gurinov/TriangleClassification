package by.bsuir.gurinov.triangleclassification.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import by.bsuir.gurinov.triangleclassification.models.Triangle;
import by.bsuir.gurinov.triangleclassification.view.Draw2D;

public class DrawFragment extends Fragment {

    private Triangle triangle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            triangle = new Triangle(bundle.getInt("side1"), bundle.getInt("side2"),
                    bundle.getInt("side3"));

            if (triangle.isValid) {
                Toast.makeText(getContext(), "Треугольник " + triangle.type, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Данный треугольник не существует",
                        Toast.LENGTH_LONG).show();
                triangle = null;
            }
        }
        return new Draw2D(this.getContext(), triangle);
    }
}
